import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Classd from './classd';
import ClassdDetail from './classd-detail';
import ClassdUpdate from './classd-update';
import ClassdDeleteDialog from './classd-delete-dialog';

const ClassdRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Classd />} />
    <Route path="new" element={<ClassdUpdate />} />
    <Route path=":id">
      <Route index element={<ClassdDetail />} />
      <Route path="edit" element={<ClassdUpdate />} />
      <Route path="delete" element={<ClassdDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClassdRoutes;
