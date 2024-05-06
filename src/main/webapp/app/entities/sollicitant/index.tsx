import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sollicitant from './sollicitant';
import SollicitantDetail from './sollicitant-detail';
import SollicitantUpdate from './sollicitant-update';
import SollicitantDeleteDialog from './sollicitant-delete-dialog';

const SollicitantRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sollicitant />} />
    <Route path="new" element={<SollicitantUpdate />} />
    <Route path=":id">
      <Route index element={<SollicitantDetail />} />
      <Route path="edit" element={<SollicitantUpdate />} />
      <Route path="delete" element={<SollicitantDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SollicitantRoutes;
