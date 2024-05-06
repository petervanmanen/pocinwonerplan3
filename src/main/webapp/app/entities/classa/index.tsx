import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Classa from './classa';
import ClassaDetail from './classa-detail';
import ClassaUpdate from './classa-update';
import ClassaDeleteDialog from './classa-delete-dialog';

const ClassaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Classa />} />
    <Route path="new" element={<ClassaUpdate />} />
    <Route path=":id">
      <Route index element={<ClassaDetail />} />
      <Route path="edit" element={<ClassaUpdate />} />
      <Route path="delete" element={<ClassaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClassaRoutes;
