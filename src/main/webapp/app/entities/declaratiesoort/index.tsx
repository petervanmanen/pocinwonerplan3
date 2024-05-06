import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Declaratiesoort from './declaratiesoort';
import DeclaratiesoortDetail from './declaratiesoort-detail';
import DeclaratiesoortUpdate from './declaratiesoort-update';
import DeclaratiesoortDeleteDialog from './declaratiesoort-delete-dialog';

const DeclaratiesoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Declaratiesoort />} />
    <Route path="new" element={<DeclaratiesoortUpdate />} />
    <Route path=":id">
      <Route index element={<DeclaratiesoortDetail />} />
      <Route path="edit" element={<DeclaratiesoortUpdate />} />
      <Route path="delete" element={<DeclaratiesoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DeclaratiesoortRoutes;
