import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Classb from './classb';
import ClassbDetail from './classb-detail';
import ClassbUpdate from './classb-update';
import ClassbDeleteDialog from './classb-delete-dialog';

const ClassbRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Classb />} />
    <Route path="new" element={<ClassbUpdate />} />
    <Route path=":id">
      <Route index element={<ClassbDetail />} />
      <Route path="edit" element={<ClassbUpdate />} />
      <Route path="delete" element={<ClassbDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClassbRoutes;
