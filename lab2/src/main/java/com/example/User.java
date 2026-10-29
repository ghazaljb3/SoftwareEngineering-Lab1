package com.example;
public class User{//each object of this class will be a user
    private String name;
    public String getName() {
        return name;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public User(String name, String password) {
        if (name.length()>50){ //checking if the username is too long (more than 50 characters)
        throw new IllegalArgumentException("Username is too long, try something shorter");
        }
        
        //counting how many @ in the username:
        //convert the string to char array:
        char[] nameArray=name.toCharArray();
        int counter=0;
        for (char c:nameArray){
            if (c=='@'){
                counter++;
            }
        }
        //check if the username has just one @:
        if (counter!=1){
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }
        //now check if the part before @ is not empty:
        //if we get here, it means that there is just one @, so if the first character is @, this is means that the part before @ is empty
        if (nameArray[0]=='@'){
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }
        //if we get here, it means that the part before @ is not empty, so I will check if the part before @ is legal:
        //valid: characters, numbers, _ % + - .
        int j=0;
        while (nameArray[j]!='@'){
            boolean isLetter;
            if (nameArray[j]>='a'&&nameArray[j]<='z'||nameArray[j]>='A'&&nameArray[j]<='Z'){
                isLetter=true;
            }
            else{
                isLetter=false;
            }

            boolean isNumber;
             if (nameArray[j]>='0'&&nameArray[j]<='9'){
                isNumber=true;
            }
            else{
                isNumber=false;
            }

            boolean isValidSpecialChar;
            if(nameArray[j]=='_'||nameArray[j]=='%'||nameArray[j]=='+'||nameArray[j]=='-'||nameArray[j]=='.'){
                isValidSpecialChar=true;
            }
            else{
                isValidSpecialChar=false;
            }

            if(!isLetter&&!isNumber&&!isValidSpecialChar){
                throw new IllegalArgumentException("Please enter a valid Email as username");
            }

            j++;
            
        }
        if (j==nameArray.length-1){ //if the part after @ is empty
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }


        boolean isLetter;
            if (nameArray[j+1]>='a'&&nameArray[j+1]<='z'||nameArray[j+1]>='A'&&nameArray[j+1]<='Z'){
                isLetter=true;
            }
            else{
                isLetter=false;
            }

            boolean isNumber;
             if (nameArray[j+1]>='0'&&nameArray[j+1]<='9'){
                isNumber=true;
            }
            else{
                isNumber=false;
            }

           

            if(!isLetter&&!isNumber){
                throw new IllegalArgumentException("Please enter a valid Email as username");
            }

        int theLasrDotIndex=-1;
        for(int i=j+1;i<nameArray.length;i++){
            if (nameArray[i]=='.'){
                theLasrDotIndex=i;
            }
        }
        if (theLasrDotIndex==-1){ //if there is no dot after @
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        if((nameArray.length - theLasrDotIndex - 1)<2){ //if the part after the last dot is less than 2 characters
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }
        
        for (int i = theLasrDotIndex + 1; i < nameArray.length; i++) {
            if (nameArray[i]>='a'&&nameArray[i]<='z'||nameArray[i] >='A'&&nameArray[i]<='Z') {
                isLetter = true;
            } else {
                isLetter = false;
            }
            if (!isLetter) {
                throw new IllegalArgumentException("Please enter a valid Email as username");
            }
        }

        for(int i=j+1;i<theLasrDotIndex;i++){
            if (nameArray[i]>='a'&&nameArray[i]<='z'||nameArray[i] >='A'&&nameArray[i]<='Z'||nameArray[i]>='0'&&nameArray[i]<='9'||nameArray[i]=='-'||nameArray[i]=='.'){
                isLetter=true;
            }
            else{
                isLetter=false;
            }
            if (!isLetter) {
                throw new IllegalArgumentException("Please enter a valid Email as username");
            }
           
        }
        this.name = name;

        //now checking the password:
        if (password.length()<8){ //checking if the password is short (less than 8 characters)
            throw new IllegalArgumentException("Your password is too short, add more characters");
        }
        if(password.length()>12){//checking if the password is long (more than 12 characters)
            throw new IllegalArgumentException("Your password is too long, try a shorter one");
        }
        boolean hasLetter=false;
        boolean hasNumber=false;
        boolean hasSymbol=false;

        char[] passwordArray=password.toCharArray(); //converting the password to char array
        for (char c:passwordArray){
            if (c>='a'&&c<='z'||c>='A'&&c<='Z'){
                hasLetter=true;
            }
            else if (c>='0'&&c<='9'){
                hasNumber=true;
            }
            else if(c=='!'||c=='@'||c=='#'||c=='$'||c=='%'||c=='^'||c=='&'||c=='*'||c=='('||c==')'||c=='_'||c=='+'){
                hasSymbol=true;
            }
            else{
                throw new IllegalArgumentException("Please enter a valid password");
            }
        }
        if(!hasLetter||!hasNumber||!hasSymbol){
            throw new IllegalArgumentException("Please enter a valid password");
        }

        this.password = password;
    }   
} 
