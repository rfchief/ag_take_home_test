AG-Assessment
===========
Take Home Project From Agoda


For Build
---------
```shell
./deploy.sh
```

For Run
---------
```shell
./run.sh $SPRING_PROFILE
```

### Arguments
- `$SPRING_PROFILE`: Profile name for Spring Boot
    - production: Profile name for production environment
    - dev: Profile name for development environment
- Example
    ```
    sh ./run.sh production
    ```

Rest API URIs
----------
- Update Hotel and Country Id list to repository
    - URI : /reposistory/update
    - Request Method : POST
    
- Get HotelId and Score
    - URI : /search/score/get
    - Request Method : GET
    - Request Parameter : List of Hotel Id, Country Id pairs
    ```
    [{"hotelId":12,"countryId":14},{"hotelId":22,"countryId":24}]
    ```
    - Return Value : List of Hotel Id and score pairs
    ```
    [{"hotelId":12,"score":0.0},{"hotelId":22,"score":5.0}]
    ```
    
- Get HotelId and Score by Async
    - URI : /search/score/async/get
    - Request Method : GET
    - Request Parameter : List of Hotel Id, Country Id pairs
    ```
    [{"hotelId":12,"countryId":14},{"hotelId":22,"countryId":24}]
    ```
    - Return Value : List of Hotel Id and score pairs
    ```
    [{"hotelId":12,"score":0.0},{"hotelId":22,"score":5.0}]
    ```

- Update hotel score for calculating
    - URI : /config/score/hotel
    - Request Method : POST, PUT
    - Request Parameter
        - name : score
        - type : int
        
- Update country score for calculating
    - URI : /config/score/country
    - Request Method : POST, PUT
    - Request Parameter
        - name : score
        - type : int

- Update parallelism for getting result by async
    - URI : /config/divide
    - Request Method : POST, PUT
    - Request Parameter
        - name : size
        - type : int