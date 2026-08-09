package dao;

import database.DBConnection;
import model.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DoctorDAO {



    // ==========================
    // ADD DOCTOR
    // ==========================

    public boolean addDoctor(Doctor doctor){


        String sql =
                "INSERT INTO doctors(name,specialization,gender,phone,email,experience) VALUES(?,?,?,?,?,?)";


        try(
                Connection con = DBConnection.getConnection();

                PreparedStatement ps = con.prepareStatement(sql)

        ){


            ps.setString(1, doctor.getName());

            ps.setString(2, doctor.getSpecialization());

            ps.setString(3, doctor.getGender());

            ps.setString(4, doctor.getPhone());

            ps.setString(5, doctor.getEmail());

            ps.setInt(6, doctor.getExperience());


            return ps.executeUpdate() > 0;


        }
        catch(Exception e){

            e.printStackTrace();

        }


        return false;

    }







    // ==========================
    // GET ALL DOCTORS
    // ==========================

    public List<Doctor> getAllDoctors(){


        List<Doctor> list =
                new ArrayList<>();


        String sql =
                "SELECT * FROM doctors ORDER BY id DESC";


        try(
                Connection con = DBConnection.getConnection();

                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery(sql)

        ){


            while(rs.next()){


                Doctor d =
                        new Doctor();


                d.setId(
                        rs.getInt("id")
                );


                d.setName(
                        rs.getString("name")
                );


                d.setSpecialization(
                        rs.getString("specialization")
                );


                d.setGender(
                        rs.getString("gender")
                );


                d.setPhone(
                        rs.getString("phone")
                );


                d.setEmail(
                        rs.getString("email")
                );


                d.setExperience(
                        rs.getInt("experience")
                );


                list.add(d);


            }


        }
        catch(Exception e){

            e.printStackTrace();

        }


        return list;

    }







    // ==========================
    // DOCTOR LIST FOR APPOINTMENT
    // ==========================

    public List<Doctor> getDoctorList(){


        List<Doctor> list =
                new ArrayList<>();


        String sql =
                "SELECT id,name FROM doctors";



        try(
                Connection con = DBConnection.getConnection();

                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery(sql)

        ){



            while(rs.next()){


                Doctor d =
                        new Doctor();


                d.setId(
                        rs.getInt("id")
                );


                d.setName(
                        rs.getString("name")
                );



                list.add(d);


            }


        }
        catch(Exception e){

            e.printStackTrace();

        }



        return list;

    }







    // ==========================
    // UPDATE DOCTOR
    // ==========================

    public boolean updateDoctor(Doctor doctor){



        String sql =
                """
                UPDATE doctors SET
        
                name=?,
        
                specialization=?,
        
                gender=?,
        
                phone=?,
        
                email=?,
        
                experience=?
        
                WHERE id=?
        
                """;



        try(
                Connection con = DBConnection.getConnection();

                PreparedStatement ps = con.prepareStatement(sql)

        ){


            ps.setString(1,doctor.getName());

            ps.setString(2,doctor.getSpecialization());

            ps.setString(3,doctor.getGender());

            ps.setString(4,doctor.getPhone());

            ps.setString(5,doctor.getEmail());

            ps.setInt(6,doctor.getExperience());

            ps.setInt(7,doctor.getId());



            return ps.executeUpdate()>0;


        }
        catch(Exception e){

            e.printStackTrace();

        }


        return false;

    }







    // ==========================
    // DELETE DOCTOR
    // ==========================

    public boolean deleteDoctor(int id){


        String sql =
                "DELETE FROM doctors WHERE id=?";



        try(
                Connection con = DBConnection.getConnection();

                PreparedStatement ps = con.prepareStatement(sql)

        ){


            ps.setInt(1,id);


            return ps.executeUpdate()>0;


        }
        catch(Exception e){

            e.printStackTrace();

        }


        return false;

    }







    // ==========================
    // SEARCH DOCTOR
    // ==========================

    public List<Doctor> searchDoctor(String keyword){


        List<Doctor> list =
                new ArrayList<>();


        String sql =
                """
                SELECT * FROM doctors
        
                WHERE name LIKE ?
        
                OR specialization LIKE ?
        
                OR phone LIKE ?
        
                """;



        try(
                Connection con = DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)

        ){



            String search =
                    "%" + keyword + "%";



            ps.setString(1,search);

            ps.setString(2,search);

            ps.setString(3,search);



            ResultSet rs =
                    ps.executeQuery();




            while(rs.next()){


                Doctor d =
                        new Doctor();


                d.setId(
                        rs.getInt("id")
                );


                d.setName(
                        rs.getString("name")
                );


                d.setSpecialization(
                        rs.getString("specialization")
                );


                d.setGender(
                        rs.getString("gender")
                );


                d.setPhone(
                        rs.getString("phone")
                );


                d.setEmail(
                        rs.getString("email")
                );


                d.setExperience(
                        rs.getInt("experience")
                );



                list.add(d);


            }



        }
        catch(Exception e){

            e.printStackTrace();

        }



        return list;

    }


}