import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Uitlaatconstructie from './uitlaatconstructie';
import UitlaatconstructieDetail from './uitlaatconstructie-detail';
import UitlaatconstructieUpdate from './uitlaatconstructie-update';
import UitlaatconstructieDeleteDialog from './uitlaatconstructie-delete-dialog';

const UitlaatconstructieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Uitlaatconstructie />} />
    <Route path="new" element={<UitlaatconstructieUpdate />} />
    <Route path=":id">
      <Route index element={<UitlaatconstructieDetail />} />
      <Route path="edit" element={<UitlaatconstructieUpdate />} />
      <Route path="delete" element={<UitlaatconstructieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UitlaatconstructieRoutes;
