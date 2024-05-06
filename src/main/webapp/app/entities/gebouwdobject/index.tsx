import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gebouwdobject from './gebouwdobject';
import GebouwdobjectDetail from './gebouwdobject-detail';
import GebouwdobjectUpdate from './gebouwdobject-update';
import GebouwdobjectDeleteDialog from './gebouwdobject-delete-dialog';

const GebouwdobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gebouwdobject />} />
    <Route path="new" element={<GebouwdobjectUpdate />} />
    <Route path=":id">
      <Route index element={<GebouwdobjectDetail />} />
      <Route path="edit" element={<GebouwdobjectUpdate />} />
      <Route path="delete" element={<GebouwdobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GebouwdobjectRoutes;
