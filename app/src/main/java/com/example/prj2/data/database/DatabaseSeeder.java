package com.example.prj2.data.database;

import android.content.Context;

import com.example.prj2.data.entity.Movie;
import com.example.prj2.data.entity.Showtime;
import com.example.prj2.data.entity.Theater;
import com.example.prj2.data.entity.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSeeder {

    public static void seed(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);

        // Seed Users
        if (db.userDao().countByEmail("user@gmail.com") == 0) {
            User user = new User("user", "user@gmail.com", "123456", "0901234567");
            db.userDao().insert(user);
        }

        // Seed Movies
        if (db.movieDao().count() == 0) {
            List<Movie> movies = new ArrayList<>();
            movies.add(new Movie(
                "Avengers: Endgame",
                "Action/Adventure",
                181,
                "Sau sự kiện tàn khốc của Avengers: Infinity War, vũ trụ đang trong đống đổ nát. Với sự giúp đỡ của những đồng minh còn lại, các Avengers tập hợp lại để đảo ngược hành động của Thanos và khôi phục lại sự cân bằng của vũ trụ.",
                9.0f,
                2023
            ));
            movies.add(new Movie(
                "The Dark Knight",
                "Action/Crime",
                152,
                "Khi mối đe dọa được biết đến là Joker xuất hiện từ sự hỗn loạn và tàn phá người dân Gotham City, Batman phải chấp nhận một trong những thử thách tâm lý và thể chất lớn nhất để chiến đấu với bất công.",
                9.0f,
                2023
            ));
            movies.add(new Movie(
                "Inception",
                "Sci-Fi/Thriller",
                148,
                "Một tên trộm đánh cắp bí mật của công ty thông qua việc sử dụng công nghệ chia sẻ giấc mơ được giao nhiệm vụ đảo ngược – cài một ý tưởng vào tâm trí của CEO.",
                8.8f,
                2023
            ));
            movies.add(new Movie(
                "Interstellar",
                "Sci-Fi/Drama",
                169,
                "Nhóm thám hiểm du hành qua một con sâu không gian trong không gian trong nỗ lực đảm bảo sự sống còn của nhân loại.",
                8.6f,
                2023
            ));
            movies.add(new Movie(
                "Spider-Man: No Way Home",
                "Action/Adventure",
                148,
                "Với danh tính của Spider-Man bị tiết lộ cho toàn thế giới, Peter Parker cầu xin Doctor Strange để quên đi điều này. Khi một phép thuật đi sai, những kẻ thù nguy hiểm bắt đầu xuất hiện.",
                8.2f,
                2023
            ));
            movies.add(new Movie(
                "Top Gun: Maverick",
                "Action/Drama",
                130,
                "Sau hơn 30 năm phục vụ với tư cách là một trong những phi công hàng đầu của Hải quân, Pete Mitchell đang chịu đựng hậu quả của việc thúc đẩy ranh giới như một phi công thử nghiệm.",
                8.3f,
                2023
            ));
            db.movieDao().insertAll(movies);
        }

        // Seed Theaters
        if (db.theaterDao().count() == 0) {
            List<Theater> theaters = new ArrayList<>();
            theaters.add(new Theater(
                "CGV Vincom Center",
                "15 Lê Thánh Tôn, Q.1, TP.HCM",
                150
            ));
            theaters.add(new Theater(
                "Lotte Cinema Landmark",
                "772 Điện Biên Phủ, Q.10, TP.HCM",
                120
            ));
            theaters.add(new Theater(
                "BHD Star Cineplex",
                "233 Nguyễn Trãi, Q.1, TP.HCM",
                100
            ));
            db.theaterDao().insertAll(theaters);
        }

        // Seed Showtimes
        if (db.showtimeDao().count() == 0) {
            List<Showtime> showtimes = new ArrayList<>();

            // Movie 1 - Avengers: Endgame at Theater 1 & 2
            showtimes.add(new Showtime(1, 1, "2024-03-15", "09:00", 80000, 40));
            showtimes.add(new Showtime(1, 1, "2024-03-15", "14:00", 90000, 40));
            showtimes.add(new Showtime(1, 2, "2024-03-15", "19:00", 100000, 40));
            showtimes.add(new Showtime(1, 2, "2024-03-16", "11:30", 90000, 40));

            // Movie 2 - The Dark Knight at Theater 1 & 3
            showtimes.add(new Showtime(2, 1, "2024-03-16", "16:30", 90000, 40));
            showtimes.add(new Showtime(2, 3, "2024-03-16", "21:30", 80000, 40));
            showtimes.add(new Showtime(2, 3, "2024-03-17", "14:00", 85000, 40));

            // Movie 3 - Inception at Theater 2 & 3
            showtimes.add(new Showtime(3, 2, "2024-03-17", "09:00", 80000, 40));
            showtimes.add(new Showtime(3, 2, "2024-03-17", "19:00", 100000, 40));
            showtimes.add(new Showtime(3, 3, "2024-03-18", "11:30", 85000, 40));

            // Movie 4 - Interstellar at Theater 1
            showtimes.add(new Showtime(4, 1, "2024-03-18", "16:30", 90000, 40));
            showtimes.add(new Showtime(4, 1, "2024-03-18", "21:30", 100000, 40));

            // Movie 5 - Spider-Man at Theater 2
            showtimes.add(new Showtime(5, 2, "2024-03-19", "09:00", 80000, 40));
            showtimes.add(new Showtime(5, 2, "2024-03-19", "14:00", 90000, 40));

            // Movie 6 - Top Gun: Maverick at Theater 3
            showtimes.add(new Showtime(6, 3, "2024-03-20", "11:30", 85000, 40));
            showtimes.add(new Showtime(6, 3, "2024-03-20", "19:00", 120000, 40));

            db.showtimeDao().insertAll(showtimes);
        }
    }
}
