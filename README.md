# CoolUp
Proggetto universitario in collaborazione con Elia Benvenuti (Socio)

Il progetto consiste nel creare un'applicazione android.

L'applicazione memorizza i prodotti che si vogliono aggiungere nel proprio frigorifero, come ad esempio la spesa che si fa al supermercato. 
Il target dell'applicazione è riferito sia alle famiglie che ai ristoratori (maggiormente ai secondi). 

Ogni prodotto si puo scanerizzare con la fotocamera del cellulare e quindi l'app aggiunge il prodotto nell'app in automatico cercandolo tra i vari prodotti memorizzati 
in un server dedicato e strutturato appositamente per l'app. 
Dopodichè l'applicazione mosterà a schermo il prodotto scannerizzato con tutte le sue informazioni, l'utente può scegliere se aggiungere la data di scadenza del prodotto per avere in futuro un reminder della scadenza dell'alimento. 

Nel progetto è presente anche un sensore (GM65) che collegato ad un arduino leggera il codice a barre del prodotto e comunicherà all'arduino il codice e tramite un sensore bluetooth (HC-05) comunicherà all'applicazione del prodotto scannerizzato. 

/**************************************************************************/
<br><b>SERVER COOL-UP</b>

Come server, l'applicaione cool-up sfrutto una piattaforma chiamata firebase. Attraverso questa piattaforma ( resa disponibile dalla google),
l'app potra memorizzare i dati su un cloud, e fare registrazione e accesso di un utente. 

/**************************************************************************/
<br><b>PROGETTO LETTORE BARCODE</b>

Hardware per effettuare il progetto:
<ul>
  <li>Arduino</li>
  <li>Sensore GMS65</li>
  <li>Sensore HC-05</li>
  <li>Sensore lcd</li>
</ul>

/**************************************************************************/
<br><b>CONTATTI</b>
<br>Visitate il sito <a href = "http://coolup.educationhost.cloud">Cool Up</a> <br>
Per contattarci scrivere alla mail di supporto <a href= "mailto:Coolup.app@gmail.com">Cool-Up support</a>
