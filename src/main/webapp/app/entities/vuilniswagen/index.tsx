import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vuilniswagen from './vuilniswagen';
import VuilniswagenDetail from './vuilniswagen-detail';
import VuilniswagenUpdate from './vuilniswagen-update';
import VuilniswagenDeleteDialog from './vuilniswagen-delete-dialog';

const VuilniswagenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vuilniswagen />} />
    <Route path="new" element={<VuilniswagenUpdate />} />
    <Route path=":id">
      <Route index element={<VuilniswagenDetail />} />
      <Route path="edit" element={<VuilniswagenUpdate />} />
      <Route path="delete" element={<VuilniswagenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VuilniswagenRoutes;
