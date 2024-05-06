import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Classg from './classg';
import ClassgDetail from './classg-detail';
import ClassgUpdate from './classg-update';
import ClassgDeleteDialog from './classg-delete-dialog';

const ClassgRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Classg />} />
    <Route path="new" element={<ClassgUpdate />} />
    <Route path=":id">
      <Route index element={<ClassgDetail />} />
      <Route path="edit" element={<ClassgUpdate />} />
      <Route path="delete" element={<ClassgDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClassgRoutes;
