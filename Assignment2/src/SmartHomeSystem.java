import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class SmartHomeSystem {
    private ArrayList<Device> devices = new ArrayList<>();

    /**
     * Adds a device to the list of devices based on the given command.
     *
     * @param elements the array of command elements that specifies the device to be added
     * @throws SmartHomeSystemException if the command is erroneous or if there is already a device with the same name
     * @throws IOException              if there is an error reading or writing to the input or output file
     */
    private void addDevice(String[] elements) throws IOException, SmartHomeSystemException {
        int name = CheckAndLook.lookupDevice(elements[2], devices);
        switch (elements[1]) {
            case "SmartPlug":
                if (elements.length >= 3) {
                    if (name == -1) {
                        if (elements.length == 3) {
                            devices.add(new SmartPlug(elements[2]));
                        } else if (elements.length == 4) {
                            devices.add(new SmartPlug(elements[2], elements[3]));
                        } else if (elements.length == 5) {
                            devices.add(new SmartPlug(elements[2], elements[3], Double.parseDouble(elements[4])));
                        }
                    } else {
                        throw new SmartHomeSystemException("There is already a smart device with same name!");
                    }
                } else {
                    throw new SmartHomeSystemException("Erroneous command!");
                }


                break;
            case "SmartCamera":
                if (elements.length >= 4) {
                    if (name == -1) {
                        if (elements.length == 4) {
                            devices.add(new SmartCamera(elements[2], Double.parseDouble(elements[3])));
                        } else if (elements.length == 5) {
                            devices.add(new SmartCamera(elements[2], Double.parseDouble(elements[3]), elements[4]));
                        }
                    } else {
                        throw new SmartHomeSystemException("There is already a smart device with same name!");
                    }
                } else {
                    throw new SmartHomeSystemException("Erroneous command!");
                }

                break;
            case "SmartLamp":
                if (elements.length >= 3) {
                    if (name == -1) {
                        if (elements.length == 3) {
                            devices.add(new SmartLamp(elements[2]));
                        } else if (elements.length == 4) {
                            devices.add(new SmartLamp(elements[2], elements[3]));
                        } else if (elements.length == 6) {
                            devices.add(new SmartLamp(elements[2], elements[3], Integer.parseInt(elements[4]), Integer.parseInt(elements[5])));
                        }
                    } else {
                        throw new SmartHomeSystemException("There is already a smart device with same name!");
                    }
                } else {
                    throw new SmartHomeSystemException("Erroneous command!");
                }

                break;
            case "SmartColorLamp":
                if (elements.length >= 3) {
                    if (name == -1) {
                        if (elements.length == 3) {
                            devices.add(new SmartLampWithColor(elements[2]));
                        } else if (elements.length == 4) {
                            devices.add(new SmartLampWithColor(elements[2], elements[3]));
                        } else if (elements.length == 6) {
                            try {
                                devices.add(new SmartLampWithColor(elements[2], elements[3], Integer.parseInt(elements[4]), Integer.parseInt(elements[5])));
                            } catch (Exception ex) {
                                devices.add(new SmartLampWithColor(elements[2], elements[3], elements[4], Integer.parseInt(elements[5])));
                            }
                        }
                    } else {
                        throw new SmartHomeSystemException("There is already a smart device with same name!");
                    }
                }

                break;
        }
    }


    /**
     * Removes the device with the given name from the list of devices, switches it off and writes the information about it to the output file.
     *
     * @param deviceName the name of the device to be removed
     * @throws SmartHomeSystemException if the device with the given name does not exist in the list of devices
     * @throws IOException              if there is an error writing to the output file
     */
    private void removeDevice(String deviceName, String outFileName) throws SmartHomeSystemException, IOException {
        int index = CheckAndLook.lookupDevice(deviceName, devices);
        if (index < 0) {
            throw new SmartHomeSystemException("There is not such a device!");
        } else {
            FileIO.writeToFile("SUCCESS: Information about removed smart device is as follows:",outFileName);
            this.devices.get(index).switchOff();
            FileIO.writeToFile(this.devices.get(index).toString(),outFileName);
            this.devices.remove(this.devices.get(index));
        }

    }

    /**
     * This method generates a Z report, which includes information about all devices sorted by switch time.
     * The report is written to a file named "output.txt".
     *
     * @param dArray An ArrayList containing all devices whose information will be included in the report.
     * @throws IOException If there is an error while writing to the file.
     */
    private void zReport(ArrayList<Device> dArray, String outFileName) throws IOException {
        FileIO.writeToFile("Time is:\t" + Time.getStrTime(),outFileName);
        dArray.sort(new DeviceComparator(dArray));
        for (Device d : dArray) {
            FileIO.writeToFile(d.toString(),outFileName);
        }

    }



    /**
     * The method is responsible for parsing and executing commands based on the strings in the ArrayList.
     * The method writes the command to a file and executes a sequence of commands.
     * The first command in the list must be "SetInitialTime".
     * After the initial time is set, the method loops through the remaining commands and executes them one by one.
     * Each command is split into its individual elements, and a switch statement is used to determine which method to call based on the first element of the command.
     * If there is no ZReport command at the end of the file method executes it anyway.
     * If an error is encountered during the execution of a command, an error message is written to the file.
     *
     * @param lines ArrayList of Strings from the input file
     * @throws ParseException if an error occurs while parsing a date or time string
     * @throws IOException    If there is an error while writing to the file.
     */
    private void executeCommands(ArrayList<String> lines , String outFileName) throws ParseException, IOException, SmartHomeSystemException {
        FileIO.writeToFile("COMMAND: " + lines.get(0),outFileName);
        String[] firstCommand = lines.get(0).split("\t");
        try{
            try {
                if (firstCommand[0].equals("SetInitialTime") && firstCommand.length == 2) {
                    Time.setInitialTime(firstCommand[1],outFileName);
                } else {
                    throw new SmartHomeSystemException("First command must be set initial time! Program is going to terminate!");

                }
            } catch (SmartHomeSystemException e) {
                FileIO.writeToFile(e.getMessage(),outFileName);
                System.exit(0);
            }catch(Exception e){ // if there is some unexpected error at initialTime command
                throw new SmartHomeSystemException("First command must be set initial time! Program is going to terminate!");
            }
        }catch (SmartHomeSystemException e){
            FileIO.writeToFile(e.getMessage(),outFileName);
            System.exit(0);
        }


        for (int i = 1; i < lines.size(); i++) {
            try {
                FileIO.writeToFile("COMMAND: " + lines.get(i),outFileName);
                String[] elements = lines.get(i).split("\t");
                switch (elements[0]) {
                    case "SetTime":
                        if (elements.length == 2) {
                            Time.setTime(elements[1], this.devices);
                        } else {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }
                        break;
                    case "SkipMinutes":
                        if (elements.length == 2) {
                            Time.skipMinutes(elements, this.devices);
                        } else {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }
                        break;
                    case "Nop":
                        if (elements.length == 1) {
                            Time.nop(this.devices);
                        } else {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }
                        break;
                    case "Remove":
                        if (elements.length == 2) {
                            this.removeDevice(elements[1],outFileName);
                        } else {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }
                        break;
                    case "SetSwitchTime":
                        if (elements.length == 3) {
                            int index = CheckAndLook.lookupDevice(elements[1], devices);
                            if (index == -1) {
                                throw new SmartHomeSystemException("There is not such a device!");
                            } else {
                                this.devices.get(index).setSwitchTime(elements[2],devices);

                            }
                        } else {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }
                        break;
                    case "Switch":
                        if (elements.length == 3) {
                            int index = CheckAndLook.lookupDevice(elements[1], devices);
                            if (index == -1) {
                                throw new SmartHomeSystemException("There is not such a device!");
                            } else {
                                this.devices.get(index).switchOnOff(elements[2]);
                            }
                        } else {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }
                        break;
                    case "ChangeName":
                        if (elements.length < 3) {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }
                        if (elements[1].equals(elements[2])) {
                            throw new SmartHomeSystemException("Both of the names are the same, nothing changed!");
                        } else {
                            int index = CheckAndLook.lookupDevice(elements[1], devices);
                            int index2 = CheckAndLook.lookupDevice(elements[2], devices);
                            if (index == -1) {
                                throw new SmartHomeSystemException("There is not such a device!");
                            } else {
                                if (index2 == -1) {
                                    this.devices.get(index).changeName(elements[2]);
                                } else {
                                    throw new SmartHomeSystemException("There is already a smart device with same name!");
                                }
                            }
                        }

                        break;
                    case "PlugIn":
                        if (elements.length == 3) {
                            int index = CheckAndLook.lookupDevice(elements[1], devices);
                            if (index != -1) {
                                if (this.devices.get(index) instanceof SmartPlug) {
                                    ((SmartPlug) devices.get(index)).plugIn(Integer.parseInt(elements[2]));
                                } else {
                                    throw new SmartHomeSystemException("This device is not a smart plug!");
                                }
                            }else{
                                throw new SmartHomeSystemException("There is not such a device!");
                            }
                        } else {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }
                        break;
                    case "PlugOut":
                        if (elements.length == 2) {
                            int index = CheckAndLook.lookupDevice(elements[1], devices);
                            if (index != -1) {
                                if (this.devices.get(index) instanceof SmartPlug) {
                                    ((SmartPlug) devices.get(index)).plugOut();
                                } else {
                                    throw new SmartHomeSystemException("This device is not a smart plug!");
                                }
                            }else{
                                throw new SmartHomeSystemException("There is not such a device!");
                            }
                        } else {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }
                        break;
                    case "SetKelvin":
                        if (elements.length == 3) {
                            int index = CheckAndLook.lookupDevice(elements[1], devices);
                            if (index != -1) {
                                if (this.devices.get(index) instanceof SmartLamp) {
                                    ((SmartLamp) devices.get(index)).setKelvin(Integer.parseInt(elements[2]));
                                } else {
                                    throw new SmartHomeSystemException("This device is not a smart lamp!");
                                }
                            }else{
                                throw new SmartHomeSystemException("There is not such a device!");
                            }
                        } else {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }

                        break;
                    case "SetBrightness":
                        if (elements.length == 3) {
                            int index = CheckAndLook.lookupDevice(elements[1], devices);
                            if (index != -1) {
                                if (this.devices.get(index) instanceof SmartLampWithColor) {
                                    ((SmartLamp) devices.get(index)).setBrightness(Integer.parseInt(elements[2]));
                                } else {
                                    throw new SmartHomeSystemException("This device is not a smart lamp!");
                                }
                            }else{
                                throw new SmartHomeSystemException("There is not such a device!");
                            }
                        } else {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }
                        break;
                    case "SetColorCode":
                        if (elements.length == 3) {
                            int index = CheckAndLook.lookupDevice(elements[1], devices);
                            if (index != -1) {
                                if (this.devices.get(index) instanceof SmartLampWithColor) {
                                    ((SmartLampWithColor) devices.get(index)).setColorCode(elements[2]);
                                } else {
                                    throw new SmartHomeSystemException("This device is not a smart color lamp!");
                                }
                            }else{
                                throw new SmartHomeSystemException("There is not such a device!");
                            }
                        } else {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }
                        break;
                    case "SetWhite":
                        if (elements.length == 4) {
                            int index = CheckAndLook.lookupDevice(elements[1], devices);
                            if (index != -1) {
                                if (this.devices.get(index) instanceof SmartLamp) {
                                    ((SmartLamp) devices.get(index)).setWhite(Integer.parseInt(elements[2]), Integer.parseInt(elements[3]));
                                } else {
                                    throw new SmartHomeSystemException("This device is not a smart lamp!");
                                }
                            }else{
                                throw new SmartHomeSystemException("There is not such a device!");
                            }
                        } else {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }

                        break;
                    case "SetColor":
                        if (elements.length == 4) {
                            int index = CheckAndLook.lookupDevice(elements[1], devices);
                            if (index != -1) {
                                if (this.devices.get(index) instanceof SmartLampWithColor) {
                                    ((SmartLampWithColor) devices.get(index)).setColor(elements[2], Integer.parseInt(elements[3]));
                                } else {
                                    throw new SmartHomeSystemException("This device is not a smart color lamp!");
                                }
                            }else{
                                throw new SmartHomeSystemException("There is not such a device!");
                            }
                        } else {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }

                        break;
                    case "Add":
                        addDevice(elements);
                        break;
                    case "ZReport":
                        if (elements.length == 1) {
                            zReport(devices,outFileName);
                        } else {
                            throw new SmartHomeSystemException("Erroneous command!");
                        }
                        break;
                    default:
                        throw new SmartHomeSystemException("Erroneous command!");
                }

            } catch (NumberFormatException e) { // integer parsing errors
                FileIO.writeToFile("ERROR: Erroneous command!",outFileName);
            } catch (SmartHomeSystemException e) {
                FileIO.writeToFile(e.getMessage(),outFileName);
            }
            catch (Exception ex){
                FileIO.writeToFile("Something unexpected happened.", outFileName);
            }
        }
        if (!(lines.get(lines.size() - 1).
                equals("ZReport"))) {
            FileIO.writeToFile("ZReport:",outFileName);
            zReport(devices,outFileName);

        }


    }

    /**
     * Executes commands from an input file and writes the results to an output file.
     * If the input file is empty, an error message is written to the output file.
     *
     * @throws IOException    If there is an error in input/output operations.
     * @throws ParseException If there is an error in parsing date/time.
     */
    public void run(String input, String output) throws IOException, ParseException {
        FileIO.cleanFile(output);
        try {
            ArrayList<String> lines = FileIO.readFromFile(input);
            if (lines.isEmpty()) {
                FileIO.writeToFile("ERROR: Command file is empty!",output);
            } else {
                executeCommands(lines,output);
            }
        } catch (SmartHomeSystemException e) {
            FileIO.writeToFile(e.getMessage(),output);
        }


    }

}
