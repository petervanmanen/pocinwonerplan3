import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Classc from './classc';
import ClasscDetail from './classc-detail';
import ClasscUpdate from './classc-update';
import ClasscDeleteDialog from './classc-delete-dialog';

const ClasscRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Classc />} />
    <Route path="new" element={<ClasscUpdate />} />
    <Route path=":id">
      <Route index element={<ClasscDetail />} />
      <Route path="edit" element={<ClasscUpdate />} />
      <Route path="delete" element={<ClasscDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClasscRoutes;
