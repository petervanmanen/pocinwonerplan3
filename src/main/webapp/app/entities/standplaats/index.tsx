import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Standplaats from './standplaats';
import StandplaatsDetail from './standplaats-detail';
import StandplaatsUpdate from './standplaats-update';
import StandplaatsDeleteDialog from './standplaats-delete-dialog';

const StandplaatsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Standplaats />} />
    <Route path="new" element={<StandplaatsUpdate />} />
    <Route path=":id">
      <Route index element={<StandplaatsDetail />} />
      <Route path="edit" element={<StandplaatsUpdate />} />
      <Route path="delete" element={<StandplaatsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StandplaatsRoutes;
