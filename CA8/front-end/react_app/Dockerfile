# stage1 as builder
# FROM node:10-alpine as builder

# # copy the package.json to install dependencies
# COPY package.json package-lock.json ./

# # Install the dependencies and make the folder
# RUN npm install && mkdir /react-ui && mv ./node_modules ./react-ui

# WORKDIR /react-ui

# COPY . .

# # Build the project and copy the files
# RUN npm run build


# FROM nginx:alpine

# #!/bin/sh

# COPY ./.nginx/nginx.conf /etc/nginx/nginx.conf

# ## Remove default nginx index page
# RUN rm -rf /usr/share/nginx/html/*

# # Copy from the stahg 1
# COPY --from=builder /react-ui/build /usr/share/nginx/html

# EXPOSE 3000 80

# ENTRYPOINT ["nginx", "-g", "daemon off;"]


# Commands
# docker build -f DockerFile -t front .
# docker run -d --name front -p 3000:8080 front



#####################################
# STAGE ONE: BUILD FRONT_END IMAGE  #
#####################################

# Use the official image as a parent image.
FROM node:alpine as front-stage

# Set the working directory.
WORKDIR /usr/src/app

# Copy the file from your host to your current location.
COPY package.json .

# Run the command inside your image filesystem.
RUN npm install

# Copy the rest of your app's source code from your host to your image filesystem.
COPY . .

# Run the specified command within the container.
RUN npm run build

#################################
# STAGE TWO: BUILD NGINX IMAGE  #
#################################

FROM nginx:alpine

# Instruct docker to copy built folder from stage one
COPY --from=front-stage /usr/src/app/build/ /usr/share/nginx/html

# Copy nginx configuration file into container
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80 to outside of the container
EXPOSE 80

# Directive tell that nginx should stay in the foreground,
# Because for containers, it is useful as best practice is one process = one container.
CMD ["nginx", "-g", "daemon off;"]


# commands:
# sudo docker build -t [IMAGE_NAME+TAG] [LOCATION OF DOCKER FILE]
# sudo docker run -it --publish 3000:3000 --detach --name [CONTAINER_NAME] [IMAGE_NAME]
# docker stop