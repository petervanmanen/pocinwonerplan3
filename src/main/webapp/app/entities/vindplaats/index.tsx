import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vindplaats from './vindplaats';
import VindplaatsDetail from './vindplaats-detail';
import VindplaatsUpdate from './vindplaats-update';
import VindplaatsDeleteDialog from './vindplaats-delete-dialog';

const VindplaatsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vindplaats />} />
    <Route path="new" element={<VindplaatsUpdate />} />
    <Route path=":id">
      <Route index element={<VindplaatsDetail />} />
      <Route path="edit" element={<VindplaatsUpdate />} />
      <Route path="delete" element={<VindplaatsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VindplaatsRoutes;
