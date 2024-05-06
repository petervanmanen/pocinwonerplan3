import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Classj from './classj';
import ClassjDetail from './classj-detail';
import ClassjUpdate from './classj-update';
import ClassjDeleteDialog from './classj-delete-dialog';

const ClassjRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Classj />} />
    <Route path="new" element={<ClassjUpdate />} />
    <Route path=":id">
      <Route index element={<ClassjDetail />} />
      <Route path="edit" element={<ClassjUpdate />} />
      <Route path="delete" element={<ClassjDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClassjRoutes;
