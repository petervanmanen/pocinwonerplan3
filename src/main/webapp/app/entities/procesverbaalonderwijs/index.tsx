import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Procesverbaalonderwijs from './procesverbaalonderwijs';
import ProcesverbaalonderwijsDetail from './procesverbaalonderwijs-detail';
import ProcesverbaalonderwijsUpdate from './procesverbaalonderwijs-update';
import ProcesverbaalonderwijsDeleteDialog from './procesverbaalonderwijs-delete-dialog';

const ProcesverbaalonderwijsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Procesverbaalonderwijs />} />
    <Route path="new" element={<ProcesverbaalonderwijsUpdate />} />
    <Route path=":id">
      <Route index element={<ProcesverbaalonderwijsDetail />} />
      <Route path="edit" element={<ProcesverbaalonderwijsUpdate />} />
      <Route path="delete" element={<ProcesverbaalonderwijsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProcesverbaalonderwijsRoutes;
