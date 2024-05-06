import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Formelehistorie from './formelehistorie';
import FormelehistorieDetail from './formelehistorie-detail';
import FormelehistorieUpdate from './formelehistorie-update';
import FormelehistorieDeleteDialog from './formelehistorie-delete-dialog';

const FormelehistorieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Formelehistorie />} />
    <Route path="new" element={<FormelehistorieUpdate />} />
    <Route path=":id">
      <Route index element={<FormelehistorieDetail />} />
      <Route path="edit" element={<FormelehistorieUpdate />} />
      <Route path="delete" element={<FormelehistorieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FormelehistorieRoutes;
