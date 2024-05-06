import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Classh from './classh';
import ClasshDetail from './classh-detail';
import ClasshUpdate from './classh-update';
import ClasshDeleteDialog from './classh-delete-dialog';

const ClasshRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Classh />} />
    <Route path="new" element={<ClasshUpdate />} />
    <Route path=":id">
      <Route index element={<ClasshDetail />} />
      <Route path="edit" element={<ClasshUpdate />} />
      <Route path="delete" element={<ClasshDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClasshRoutes;
