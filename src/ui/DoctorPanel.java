package ui;

import dao.DoctorDAO;
import model.Doctor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class DoctorPanel extends JPanel {


    private JTextField nameField;
    private JTextField specializationField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField experienceField;

    private JComboBox<String> genderBox;

    private JTable table;

    private DefaultTableModel model;

    private JTextField searchField;

    private DoctorDAO dao;



    public DoctorPanel(){


        dao = new DoctorDAO();


        setLayout(
                new BorderLayout()
        );


        setBackground(
                new Color(240,245,250)
        );


        createUI();


        loadDoctors();

    }






    private void createUI(){



        // ==========================
        // HEADER
        // ==========================


        JPanel header =
                new JPanel(new BorderLayout());


        header.setBackground(
                new Color(240,245,250)
        );



        JLabel title =
                new JLabel(
                        "Doctor Management"
                );


        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        32
                )
        );


        title.setBorder(
                new EmptyBorder(
                        20,20,20,20
                )
        );



        header.add(
                title,
                BorderLayout.WEST
        );



        searchField =
                new JTextField();



        searchField.setPreferredSize(
                new Dimension(
                        250,
                        35
                )
        );



        JButton searchBtn =
                new JButton(
                        "Search"
                );



        JPanel searchPanel =
                new JPanel();


        searchPanel.add(searchField);

        searchPanel.add(searchBtn);



        header.add(
                searchPanel,
                BorderLayout.EAST
        );



        add(
                header,
                BorderLayout.NORTH
        );







        // ==========================
        // FORM
        // ==========================


        JPanel form =
                new JPanel(
                        new GridLayout(
                                4,
                                4,
                                15,
                                15
                        )
                );



        form.setBackground(
                Color.WHITE
        );


        form.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );



        nameField =
                new JTextField();


        specializationField =
                new JTextField();


        phoneField =
                new JTextField();


        emailField =
                new JTextField();


        experienceField =
                new JTextField();




        genderBox =
                new JComboBox<>(
                        new String[]{
                                "Male",
                                "Female",
                                "Other"
                        }
                );




        form.add(
                new JLabel("Name")
        );

        form.add(
                nameField
        );



        form.add(
                new JLabel("Specialization")
        );

        form.add(
                specializationField
        );



        form.add(
                new JLabel("Gender")
        );

        form.add(
                genderBox
        );



        form.add(
                new JLabel("Phone")
        );

        form.add(
                phoneField
        );



        form.add(
                new JLabel("Email")
        );

        form.add(
                emailField
        );



        form.add(
                new JLabel("Experience")
        );

        form.add(
                experienceField
        );




        JButton addBtn =
                new JButton(
                        "Add Doctor"
                );


        JButton updateBtn =
                new JButton(
                        "Update"
                );


        JButton deleteBtn =
                new JButton(
                        "Delete"
                );



        form.add(addBtn);

        form.add(updateBtn);

        form.add(deleteBtn);








        // ==========================
        // TABLE
        // ==========================


        model =
                new DefaultTableModel();



        model.setColumnIdentifiers(
                new String[]{

                        "ID",
                        "Name",
                        "Specialization",
                        "Gender",
                        "Phone",
                        "Email",
                        "Experience"

                }
        );



        table =
                new JTable(model);



        table.setRowHeight(
                30
        );



        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );



        JScrollPane scroll =
                new JScrollPane(
                        table
                );




        JPanel center =
                new JPanel(
                        new BorderLayout()
                );


        center.setBackground(
                new Color(240,245,250)
        );


        center.setBorder(
                new EmptyBorder(
                        10,
                        20,
                        20,
                        20
                )
        );



        center.add(
                form,
                BorderLayout.NORTH
        );


        center.add(
                scroll,
                BorderLayout.CENTER
        );



        add(
                center,
                BorderLayout.CENTER
        );







        // ==========================
        // BUTTON ACTIONS
        // ==========================



        addBtn.addActionListener(e -> {


            Doctor d =
                    getDoctorFromForm();



            if(d==null)
                return;



            if(dao.addDoctor(d)){


                JOptionPane.showMessageDialog(
                        this,
                        "Doctor Added Successfully"
                );


                loadDoctors();

                clearForm();

            }


        });







        updateBtn.addActionListener(e -> {



            int row =
                    table.getSelectedRow();



            if(row==-1){


                JOptionPane.showMessageDialog(
                        this,
                        "Select Doctor First"
                );


                return;

            }



            Doctor d =
                    getDoctorFromForm();



            if(d==null)
                return;



            d.setId(
                    Integer.parseInt(
                            model.getValueAt(row,0)
                                    .toString()
                    )
            );



            if(dao.updateDoctor(d)){


                JOptionPane.showMessageDialog(
                        this,
                        "Updated Successfully"
                );


                loadDoctors();

                clearForm();

            }



        });






        deleteBtn.addActionListener(e -> {



            int row =
                    table.getSelectedRow();



            if(row!=-1){



                int id =
                        Integer.parseInt(
                                model.getValueAt(row,0)
                                        .toString()
                        );



                dao.deleteDoctor(id);



                loadDoctors();


                clearForm();

            }


        });






        searchBtn.addActionListener(e -> {


            String text =
                    searchField.getText();



            if(text.isEmpty()){

                loadDoctors();

            }
            else{

                loadTable(
                        dao.searchDoctor(text)
                );

            }


        });








        table.getSelectionModel()
                .addListSelectionListener(e -> {


                    int row =
                            table.getSelectedRow();



                    if(row!=-1){



                        nameField.setText(
                                model.getValueAt(row,1)
                                        .toString()
                        );



                        specializationField.setText(
                                model.getValueAt(row,2)
                                        .toString()
                        );



                        genderBox.setSelectedItem(
                                model.getValueAt(row,3)
                                        .toString()
                        );



                        phoneField.setText(
                                model.getValueAt(row,4)
                                        .toString()
                        );



                        emailField.setText(
                                model.getValueAt(row,5)
                                        .toString()
                        );



                        experienceField.setText(
                                model.getValueAt(row,6)
                                        .toString()
                        );


                    }


                });



    }







    private Doctor getDoctorFromForm(){


        Doctor d =
                new Doctor();



        d.setName(
                nameField.getText()
        );



        d.setSpecialization(
                specializationField.getText()
        );



        d.setGender(
                genderBox.getSelectedItem()
                        .toString()
        );



        d.setPhone(
                phoneField.getText()
        );



        d.setEmail(
                emailField.getText()
        );



        try{


            d.setExperience(
                    Integer.parseInt(
                            experienceField.getText()
                    )
            );


        }
        catch(Exception e){


            JOptionPane.showMessageDialog(
                    this,
                    "Experience must be number"
            );


            return null;

        }



        return d;

    }







    private void loadDoctors(){


        loadTable(
                dao.getAllDoctors()
        );


    }






    private void loadTable(
            List<Doctor> doctors
    ){


        model.setRowCount(0);



        for(Doctor d:doctors){



            model.addRow(
                    new Object[]{

                            d.getId(),

                            d.getName(),

                            d.getSpecialization(),

                            d.getGender(),

                            d.getPhone(),

                            d.getEmail(),

                            d.getExperience()

                    }
            );


        }


    }







    private void clearForm(){


        nameField.setText("");

        specializationField.setText("");

        phoneField.setText("");

        emailField.setText("");

        experienceField.setText("");

        genderBox.setSelectedIndex(0);


    }



}