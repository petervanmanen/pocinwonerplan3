import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Classf from './classf';
import ClassfDetail from './classf-detail';
import ClassfUpdate from './classf-update';
import ClassfDeleteDialog from './classf-delete-dialog';

const ClassfRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Classf />} />
    <Route path="new" element={<ClassfUpdate />} />
    <Route path=":id">
      <Route index element={<ClassfDetail />} />
      <Route path="edit" element={<ClassfUpdate />} />
      <Route path="delete" element={<ClassfDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClassfRoutes;
