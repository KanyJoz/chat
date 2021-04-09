1) Általános tömör leírás a programról:
A program egy egyszerű chat applikációt valósít meg, melyen keresztül emberek kezdeményezhetnek
egymással beszélgetést, csatlakozhatnak különböző érdeklődésű szobákhoz, és a szoba tagjaival is beszélgetésbe
elegyedhetnek.
Az alkalmazás programkódja alapvetően angol nyelven íródott, és angol nyelvű felhasználót feltételez, angol karakterkészlettel.


2) Feature lista:
    I) Asztali alkalmazás feature lista:
        - az admin felhasználót két nagy gomb fogadja a JavaFX asztali alkalamzásban, melyek a felhasználók és szobák listázására alkalmasak
        - az alkalmazást a jobb felső sarokban lévő X gombbal be lehet zárni
        - a két listázási képernyőn egy két elemes lenyiló menű van
            -- itt a Back gomb visszadob a kezdő képernyőre
            -- a Close gomb szintén bezárja az alkalamzást
        - a felhasználók adatai mellett lehetőségünk van törölni őket a Delete gombbal
        - a felhasználóknak megnézhetjük a hobbijait is egy külön ablakban
        - a szobákat lehetőségünk van törölni a Delete gombbal
        - a szobáknál meg lehet nézni, hogy milyen felhasználok tagjai az adott szobának
        - a szobáknál meg lehet nézni, hogy milyen szabályok vonatkoznak az adott szobára

    II) Webes alkalmazás feature lista:
        - a kezdőoldalon lehet keresni a felhasználók között név és hobby alapján
        - a kezdőoldalon lehet keresni a szobák között név és típus alapján
        - a Registration fülön lehet regisztrálni (jelszavak hashelve vannak)
        - a Login fülön lehet bejelentkezni
        - ha be vagyunk jelentkezve, akkor tudunk keresni a felhasználók és szobák között
        - felhasználokkal lehet beszélgetni
        - a 10 legfrissebb üzenet jelenik meg, melyet a Send-el lehet elküldeni
        - a Refresh gombbal tudunk frissíteni, ha úgy gondoljuk a másik visszaküldött már valamit
        - képet is lehet küldeni, de ez elég hosszú String-et eredményez, így nem ajánlott
        - Create Room fülönl lehet zsobát létrehozni
        - a szobákhoz lehet csatlakozni
        - meg lehet nézni, kik vannak a szobában
        - ki lehet lépni a szobából
        - meg lehet nézni a szobák adatait
        - ki lehet törölni a szobát
        - a felhasználó megnézheti saját adatait
        - a felhasználó törölheti magát


3) Extra szükséges beállítások:
A legtöbb helyen relatív útvonalak vannak, de az alkalmazás adatbázisaként szolgáló chat.db útvonala abszolútt, hogy a driver megtalálja.
Ez a chat-core modul resources mappájában van application.properties fájlban, azt át kell állítani, hogy az adatbázisra mutasson a használó gépén.
Illetve le kell tölteni és konfigurálni kell egy Tomcat szervert, ahogy gyakorlaton láttuk.
Minden más Maven Dependency, amit le kellene töltenie a rendszernek.


4) Fordítás/futtatáshoz szükséges lépések:
A webes alkalamzáshoz el kell indítani a konfigurált Tomcat szervert, ami majd a Google Chrome-ban behozza a localhoston az alkalamzást.
Az asztali alkalmazást pedig a javafx:run pluginnal kell elindítani, ahogy gyakorlaton megtanultuk.
FONTOS továbbá, hogy a sessionScope erősen van használva a webes alkalamzásban így a teszteléshez két különböző böngészőben nyissuk emg az alkalmazást,
majd ezt követően regisztráljunk két különöbőz felhasználót és azokon keresztül chateljünk.
FONTOS továbbá, hogy a fényképek küldése megoldott, de a hosszú String kicsit csúnyává teszi a weboldalt, így az a végső teszt legyen.

A projektet felraktam githubra folyamatos commitolás mellett, jelenleg private láthatóságú, de az értékelés előtt szívesen átállítom public-ra.
https://github.com/KanyJoz/chat