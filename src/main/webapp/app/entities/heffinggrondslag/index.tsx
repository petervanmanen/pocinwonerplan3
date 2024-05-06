import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Heffinggrondslag from './heffinggrondslag';
import HeffinggrondslagDetail from './heffinggrondslag-detail';
import HeffinggrondslagUpdate from './heffinggrondslag-update';
import HeffinggrondslagDeleteDialog from './heffinggrondslag-delete-dialog';

const HeffinggrondslagRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Heffinggrondslag />} />
    <Route path="new" element={<HeffinggrondslagUpdate />} />
    <Route path=":id">
      <Route index element={<HeffinggrondslagDetail />} />
      <Route path="edit" element={<HeffinggrondslagUpdate />} />
      <Route path="delete" element={<HeffinggrondslagDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HeffinggrondslagRoutes;
