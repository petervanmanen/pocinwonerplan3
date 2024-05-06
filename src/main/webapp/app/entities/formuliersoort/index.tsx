import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Formuliersoort from './formuliersoort';
import FormuliersoortDetail from './formuliersoort-detail';
import FormuliersoortUpdate from './formuliersoort-update';
import FormuliersoortDeleteDialog from './formuliersoort-delete-dialog';

const FormuliersoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Formuliersoort />} />
    <Route path="new" element={<FormuliersoortUpdate />} />
    <Route path=":id">
      <Route index element={<FormuliersoortDetail />} />
      <Route path="edit" element={<FormuliersoortUpdate />} />
      <Route path="delete" element={<FormuliersoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FormuliersoortRoutes;
