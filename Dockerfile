# docker file for npm project
# based on docker pull oraclelinux:9
# Susak Oleksandr
# DevOps 1250

# base image
FROM  oraclelinux:9

# label
LABEL maintainer="Susak"
LABEL version="1.0"
LABEL description="Nginx on  Oracle Linux"

# work directory
WORKDIR /home/susak/project

# Installing dependencies
RUN yum update -y && \
        yum install -y nginx git nano curl && \
            curl -sL https://rpm.nodesource.com/setup_14.x | bash - && \
        yum install -y nodejs

# Cloning the application repository
RUN git clone https://github.com/DevOps2-Fundamentals/example-app-nodejs-backend-react-frontend.git .



# Install npm dependencies
RUN npm install

RUN npm build


# working port
EXPOSE 80

# Default command to launch the application
CMD ["sh", "-c", "nginx -g 'daemon off;' & npm start"]


