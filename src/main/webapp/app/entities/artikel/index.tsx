import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Artikel from './artikel';
import ArtikelDetail from './artikel-detail';
import ArtikelUpdate from './artikel-update';
import ArtikelDeleteDialog from './artikel-delete-dialog';

const ArtikelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Artikel />} />
    <Route path="new" element={<ArtikelUpdate />} />
    <Route path=":id">
      <Route index element={<ArtikelDetail />} />
      <Route path="edit" element={<ArtikelUpdate />} />
      <Route path="delete" element={<ArtikelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtikelRoutes;
