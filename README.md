# Házi feladat specifikáció

Információk [itt](https://viauac00.github.io/laborok/hf)

## Mobil- és webes szoftverek
### 2023.10.21.
### CardWise
### Nemes Lili - (GLJD1D)
### lili.nemes@edu.bme.hu
### Laborvezető: Kövesdán Gábor

## Bemutatás

Az alkalmazás segítségével "tanulós" kártyákat lehet létrehozni, illetve ezek segítségével, interaktívan lehet tanulni, mely a legtöbb ember számára sokkal eredményesebb, mint az anyag passzív, újra és újra átolvasással történő tanulása. Célközönség bárki, aki egy adott témát ilyen módon szeretne megtanulni de nem szeretne papír alapú kártyákat létrehozni.

## Főbb funkciók

Az alkalmazás megnyitásakor egy splash screen majd egy login képernyő fogadja a felhasználót. A register gomb megnyomásásra egy dialógusablakban lehet regisztrálni, azaz új felhasználónevet és jelszót megadni. A belépés utáni képernyőn a felhasználó két menüpontból választhat: az egyik a My Decks, a másik pedig a My Stats. A My Stats menüpontban a felhasználót egy kalendár és különböző diagrammok fogadják, melyen látszik az elmúlt hónap aktivitása. Az apphoz egy widget is fog tartozni, amely a felhasználó streakjét, vagyis hogy hány napja használja kihagyás nélkül az appot mutatja meg. A My Decks menüpontban jelennek meg lista szerűen a felhasználó által korábban elkészített kártyacsomagok illetve egy gomb, melyre kattintva új Decket lehet létrehozni.Új Deck létrehozásakor meg kell adni egy dialógusablakban a kártyapakli címét, majd a következő oldalon szintén egy plusz gombra kattintva lehet dialógusablakban egy kártya két oldalának tartalmát megadni. Az elkészült kártyák ebben a nézetben  kártya formában jelennek majd meg, az elem oldalán található törlés és módosítás gombokkal lehet változtatásokat végezni rajtuk. Ha kész a pakli akkor a kész gomb megnyomásával lehet visszakerülni a My Decks menüpontba. Innen egy már kész paklira kattintva a pakli tartalma az előzőhez nagyon hasonló, listaszerű formátumban jelenik meg, azonban a képernyőn balra húzással egy új nézet jelenik meg, melyen a megtanult, ismételendő és nem is látott kártyákról készült chart látható, illetve három gomb: learn, delete és set deadline. A delete törli az egész kártyapaklit, a set deadlinennal be lehet állítani, hogy meddig kell megtanulni a kártyák tartalmát, és addig az alkalmazás minden nap egy, a felhasználó által beállítható időpontban értesítést küld a felhasználónak ha az még nem nyitotta meg ezt a paklit tanulás céljából. A learn lenyomásának hatására aktiválódó nézetben egy sorsolt kártyán látható kérdés lesz látható, melyre a felhasználónak választ kell adnia. A felhasználó ezután megtekintheti a helyes választ a kérdésre (mely animáció segítségével fog úgy kinézni, mintha a kártya másik oldalára fordulna) és megadhatja, hogy nem tudta, könnyű, közepes vagy nehéz volt ez a kérdés, illetve hogy szeretne-e tovább tanulni (ekkor új kérdés lesz látható), vagy abbahagyná a tanulást és kilépne a set menüjébe.



## Választott technológiák:

- UI
- Fragmentek és DialogFragmentek
- Splah screen
- Widget
- RecyclerView
- Perzisztens adattárolás
- Chartok
- Notification
- ViewPager
- Animációk
- CardView

# Házi feladat dokumentáció (ha nincs, ez a fejezet törölhető)
Az alkalmazás egy splash screennel kezdődik, melyen látható a saját logója is. Ezután a felhasználó bejelentkezhet a fiókjába, melyhez meg kell adja a felhasználónevét és jelszavát. Amennyiben még nincsen fiókja regisztrálhat egy felahsználónév és jelszó megadásával. Ezeket a bejelentkezési adatokat egy Room adatbázisban tárolja az alkalmazás. Attól függően, hogy kinek a profiljáról van szó más deck-eket, statisztikákat jelenít meg az alkalmazás, hiszen minden felhasználónak személyreszabott deckjei, statisztikái vannak. Ezután az alkalmazás átirányít a főoldalra, ahol 2 gomb taláható: MyDecks és MyStats. A MyDecks gombra kattintással egy új Fragmentre kerül a felhasználó. Ott a floating action button megnyomásával tud a felhasználó új Decket létrehozni. Először a Deck címét és leírását kell megadni. Ezután a MyDecks menü üres lista helyett RecyclerView segítségével jeleníti meg a Deckeket amiket a felhasználó létrehozott. 3 gomb van rajtuk: egy a törlésért felelős. A másodikkal szerkeszteni lehet a decket. Erre kattintva a felhasználó új fragmentre kerül. Itt tud új kártyákat felvenni a deckbe, a kérdés és a válasz megadásával. Ebből akárhányat felvehet, RecyclerView segítségével vannak megjelenítve, ráadásul CardViewt alkalmazva, hogy igazi kártyákra hasonlítsanak. A felhasználó vissza gombbal kerülhet vissza a Deckek választásához. A 3. gombra, a playre kattintva lehet elkezdeni gyakorolni az dott Deckben található kártyákat. A megjelenő kérdésre a választ a megadott Answer mezőbe kell begépelni, majd a Done gombra nyomni. Ekkor a kártya lassan, animáció segítségével megfordul és megjelenik egy pipa vagy egy x attól függően, hogy a felhasználó a helyes választ adta-e a kérdésre. Minden megválaszolt kérdés után lehetősége van a felhasználónak új kérdés kérésére vagy a tanulás abbahagyására. A kérdések sorrendje random, ez egyértelműen egy olyan pontja az alkalmazásnak amin a későbbiekben még fejleszteni lehetne. Illetve minden kérdésnél szerepel egy I don't know gomb is, melynek megnyomására a kártya megfordul adott válasz nélkül. Ez azért előnyös, mert az alkalmazás minden kártyára a Deckben nyilvántartja, hogy hányszor adott a felhasználó helyes és helytelen választ, a nem tudom válasz pedig nem számít egyiknek sem, így nem rontja a felhasználó statisztikáját. A Deckekben lévő kártyákra adott válaszok sikerességének százalékát is meg lehet tekinteni, 1-1 piecharton vannak ábrázolva Deckenként. Ezen kívül, a my stats menüpontban a felhasználó különböző statisztikákat tekinthet meg az app használatáról. Először, egy kalendárban bejelölve tekintheti meg, hogy melyek voltak azok a napok, amikor használta az alkalmazást. Majd láthat statisztikákat arról is, hogy melyik Deckjében hány kártya van. Legvégül pedig 2 leaderboardot tud itt megtekinteni, ahol a legjobb 5 játékos látszik a kérdések száma és a kérdésekre adott válaszok sikeressége szerint. Ezen kívül egy widget is készült az alkalmazáshoz, melynek célja az lett volna, hogy számontartsa a felhasználó 'streakjét' vagyi, hogy hány napja használja folyamatosan az applikációt. Azonban végül csak a widget váza készült el, mivel a használt Room Databaseben minden adat ahhoz van kötve, hogy melyik usernek az adata, hogy a dokumentáció elején lévő funkciók meg tudjanak valósulni. Ez konkrétan azt jelenti, hogy a Decknek van egy userId attribútuma, a Cardnak pedig egy deckId, így kapcsolódnak össze. A dailyStatok userIDval és deckIdval is rendelkeznek. Azonban emiatt a statok csak azután jelenhetnek meg, ha tudjuk, hogy milyen userről van szó, ez viszont csak bejelentkezésnél derül ki, így egy kezdőképernyőre rakott widget esetén nem tudjuk a jelenlegi architektúrával meghatározni, hogy melyik felhasználóról van szó, így pedig nyilván a streakjét sem. Erre lehetséges megoldás/továbbfejlesztés az lenne, ha nem csak lokálisan lenne multiuser az alkalmazás, hanem mondjuk firebase alkalmazásával.
