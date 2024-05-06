import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Formulierinhuur from './formulierinhuur';
import FormulierinhuurDetail from './formulierinhuur-detail';
import FormulierinhuurUpdate from './formulierinhuur-update';
import FormulierinhuurDeleteDialog from './formulierinhuur-delete-dialog';

const FormulierinhuurRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Formulierinhuur />} />
    <Route path="new" element={<FormulierinhuurUpdate />} />
    <Route path=":id">
      <Route index element={<FormulierinhuurDetail />} />
      <Route path="edit" element={<FormulierinhuurUpdate />} />
      <Route path="delete" element={<FormulierinhuurDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FormulierinhuurRoutes;
