import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Externebron from './externebron';
import ExternebronDetail from './externebron-detail';
import ExternebronUpdate from './externebron-update';
import ExternebronDeleteDialog from './externebron-delete-dialog';

const ExternebronRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Externebron />} />
    <Route path="new" element={<ExternebronUpdate />} />
    <Route path=":id">
      <Route index element={<ExternebronDetail />} />
      <Route path="edit" element={<ExternebronUpdate />} />
      <Route path="delete" element={<ExternebronDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ExternebronRoutes;
