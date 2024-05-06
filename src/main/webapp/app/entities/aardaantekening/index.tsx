import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aardaantekening from './aardaantekening';
import AardaantekeningDetail from './aardaantekening-detail';
import AardaantekeningUpdate from './aardaantekening-update';
import AardaantekeningDeleteDialog from './aardaantekening-delete-dialog';

const AardaantekeningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aardaantekening />} />
    <Route path="new" element={<AardaantekeningUpdate />} />
    <Route path=":id">
      <Route index element={<AardaantekeningDetail />} />
      <Route path="edit" element={<AardaantekeningUpdate />} />
      <Route path="delete" element={<AardaantekeningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AardaantekeningRoutes;
