import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Juridischeregel from './juridischeregel';
import JuridischeregelDetail from './juridischeregel-detail';
import JuridischeregelUpdate from './juridischeregel-update';
import JuridischeregelDeleteDialog from './juridischeregel-delete-dialog';

const JuridischeregelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Juridischeregel />} />
    <Route path="new" element={<JuridischeregelUpdate />} />
    <Route path=":id">
      <Route index element={<JuridischeregelDetail />} />
      <Route path="edit" element={<JuridischeregelUpdate />} />
      <Route path="delete" element={<JuridischeregelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JuridischeregelRoutes;
