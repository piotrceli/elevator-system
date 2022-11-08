Elevator System jest projektem systemu kontroli wind w budynku.

Wykorzystane technologie: 
Java 
JUnit

Spis treści:
1. Założenia ogólne
2. Dostępne funkcjonalności
3. Sposób użytkowania aplikacji

1. Założenia ogólne
System służy do obsługi do 16 wind. 
Założono, że budynek posiada piętra od 0 (parter) do 10.
Dostępne piętra dla poszczególnych wind przedstawione zostały przy użyciu int[] array - targetFloors.
W przypadku, gdy piętro jest jednym z pięter docelowych danej windy odpowiedni element w array (targetFloors) przyjmuje wartość 1.
Każda winda ma możliwość posiadania kilku wybranych pięter docelowych jednocześnie.
Winda po zajęciu wybranego piętra docelowego (targetFloors[i] = 1) zatrzymuje się na nim na jeden krok symulacji (simulateStep()), co monitorowane jest przy użyciu pola klasy isStopped. 
Aktualny kierunek jazdy windy śledzi pole klasy direction, gdzie: 
liczba dodatnia - jazda w górę, liczba ujemna - jazda w dół, 0 - winda stoi w miejscu i nie posiada żadnych pięter docelowych.

2. Dostępne funkcjonalności
viewStatus() - pozwala na wyświetlenie statusu wszystkich wind w poniższej formie:
==============================================================================================================
Elevator{id=1, currentFloor=5, targetFloors=[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], direction=0, isStopped=true}
Elevator{id=2, currentFloor=4, targetFloors=[0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1], direction=1, isStopped=false}
Elevator{id=3, currentFloor=8, targetFloors=[0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1], direction=-1, isStopped=false}
Elevator{id=4, currentFloor=5, targetFloors=[1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0], direction=1, isStopped=false}
Elevator{id=5, currentFloor=4, targetFloors=[1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0], direction=-1, isStopped=true}
==============================================================================================================

simulateStep() - funkcjonalność pozwala na wykonanie jednego kroku symulacji.
Windy jadą w odpowiednich kierunkach i zatrzymują się na docelowym piętrze (targetFloors[i] = 1) na jeden krok.
W razie potrzeby windy zmieniają kierunek po dotarciu na ostatnie docelowe piętro w pierwotnym kierunku.

updateElevatorStatus() - aktualizacja aktualnego piętra (currentFloor) oraz wybór jednego piętra docelowego dla dowolnej windy

selectFloor() - wybranie piętra dla konkretnej windy (symulacja wciśnięcia przycisku dla danego piętra w środku windy)

pickUpElevator() - zamówienie windy przez pasażera na konkretnym piętrze z uwzględnieniem kierunku jazdy.
W pierwszej kolejności wybrana zostanie najbliższa winda jadąca w tym samym kierunku, która może zabrać pasażera bez zmiany kierunku jazdy.
W przypadku braku windy spełniającej powyższy warunek, wybrana zostanie najbliższa winda, która nie jest użytkowana.
Na samym końcu pod uwagę brane są windy jadące w przeciwnym kierunku oraz windy, które jadą w tym samym kierunku, ale minęły już piętro pasażera (passengerFloor).
W tym przypadku wybrana zostanie winda, której ostatnie piętro docelowe dla aktualnego kierunku jest najbliżej piętra pasażera.

Możliwość włączenia/wyłączenia automatycznej symulacji, wykonującej kolejne kroki w określonym odstępie czasowym (7 sekund).
Automatyczna symulacja nie blokuje pozostałych funkcjonalności.

3. Sposób użytkowania aplikacji.
Aplikację należy uruchomić poprzez metodę main w klasie Main.
Do interakcji użytkownika z aplikacją wykorzystano Scanner.
W konsoli wyświetlane są kolejne kroki/możliwości postępowania dla wybranej opcji z poniższego menu

MENU:
0 - Show Menu
1 - View Elevator System's Status
2 - Simulate Step and Show Updated Status
3 - Pick Up Elevator
4 - Select Floor
5 - Update Elevator's Status
6 - ON/OFF Auto Simulation
9 - Shut Down Application

Aplikacja zawiera podstawowe walidacje w przypadku wprowadzenia złego typu danych lub danych niezgodnych z założeniami aplikacji.



