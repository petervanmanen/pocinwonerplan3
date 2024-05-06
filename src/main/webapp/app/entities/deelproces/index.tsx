import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Deelproces from './deelproces';
import DeelprocesDetail from './deelproces-detail';
import DeelprocesUpdate from './deelproces-update';
import DeelprocesDeleteDialog from './deelproces-delete-dialog';

const DeelprocesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Deelproces />} />
    <Route path="new" element={<DeelprocesUpdate />} />
    <Route path=":id">
      <Route index element={<DeelprocesDetail />} />
      <Route path="edit" element={<DeelprocesUpdate />} />
      <Route path="delete" element={<DeelprocesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DeelprocesRoutes;
