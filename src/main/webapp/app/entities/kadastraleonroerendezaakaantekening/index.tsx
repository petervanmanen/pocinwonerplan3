import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kadastraleonroerendezaakaantekening from './kadastraleonroerendezaakaantekening';
import KadastraleonroerendezaakaantekeningDetail from './kadastraleonroerendezaakaantekening-detail';
import KadastraleonroerendezaakaantekeningUpdate from './kadastraleonroerendezaakaantekening-update';
import KadastraleonroerendezaakaantekeningDeleteDialog from './kadastraleonroerendezaakaantekening-delete-dialog';

const KadastraleonroerendezaakaantekeningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kadastraleonroerendezaakaantekening />} />
    <Route path="new" element={<KadastraleonroerendezaakaantekeningUpdate />} />
    <Route path=":id">
      <Route index element={<KadastraleonroerendezaakaantekeningDetail />} />
      <Route path="edit" element={<KadastraleonroerendezaakaantekeningUpdate />} />
      <Route path="delete" element={<KadastraleonroerendezaakaantekeningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KadastraleonroerendezaakaantekeningRoutes;
