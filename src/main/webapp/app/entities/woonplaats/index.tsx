import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Woonplaats from './woonplaats';
import WoonplaatsDetail from './woonplaats-detail';
import WoonplaatsUpdate from './woonplaats-update';
import WoonplaatsDeleteDialog from './woonplaats-delete-dialog';

const WoonplaatsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Woonplaats />} />
    <Route path="new" element={<WoonplaatsUpdate />} />
    <Route path=":id">
      <Route index element={<WoonplaatsDetail />} />
      <Route path="edit" element={<WoonplaatsUpdate />} />
      <Route path="delete" element={<WoonplaatsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WoonplaatsRoutes;
