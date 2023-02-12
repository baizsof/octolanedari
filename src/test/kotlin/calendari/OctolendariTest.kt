package calendari

import java.nio.file.Paths

class OctolendariTest{

    fun test() {
        val configurationsFolder = Paths.get("src","test","resources","calendar","configuration")
        val args : Array<String> = arrayOf("octolendari",
            "--from", "2023-02-12",
            "--to", "2023-02-14",
            "--calendar", configurationsFolder.resolve("google-test.calendar").toString(),
            "--calendar", configurationsFolder.resolve("teamup-test.calendar").toString(),
            "--output", configurationsFolder.resolve("query.txt").toString()
        )

        Octolendari.main(args)
    }
}