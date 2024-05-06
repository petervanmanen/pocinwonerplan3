import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aantal from './aantal';
import AantalDetail from './aantal-detail';
import AantalUpdate from './aantal-update';
import AantalDeleteDialog from './aantal-delete-dialog';

const AantalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aantal />} />
    <Route path="new" element={<AantalUpdate />} />
    <Route path=":id">
      <Route index element={<AantalDetail />} />
      <Route path="edit" element={<AantalUpdate />} />
      <Route path="delete" element={<AantalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AantalRoutes;
