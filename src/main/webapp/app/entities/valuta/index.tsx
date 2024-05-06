import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Valuta from './valuta';
import ValutaDetail from './valuta-detail';
import ValutaUpdate from './valuta-update';
import ValutaDeleteDialog from './valuta-delete-dialog';

const ValutaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Valuta />} />
    <Route path="new" element={<ValutaUpdate />} />
    <Route path=":id">
      <Route index element={<ValutaDetail />} />
      <Route path="edit" element={<ValutaUpdate />} />
      <Route path="delete" element={<ValutaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ValutaRoutes;
