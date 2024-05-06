import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tunnelobject from './tunnelobject';
import TunnelobjectDetail from './tunnelobject-detail';
import TunnelobjectUpdate from './tunnelobject-update';
import TunnelobjectDeleteDialog from './tunnelobject-delete-dialog';

const TunnelobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Tunnelobject />} />
    <Route path="new" element={<TunnelobjectUpdate />} />
    <Route path=":id">
      <Route index element={<TunnelobjectDetail />} />
      <Route path="edit" element={<TunnelobjectUpdate />} />
      <Route path="delete" element={<TunnelobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TunnelobjectRoutes;
