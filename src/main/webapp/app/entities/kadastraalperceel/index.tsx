import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kadastraalperceel from './kadastraalperceel';
import KadastraalperceelDetail from './kadastraalperceel-detail';
import KadastraalperceelUpdate from './kadastraalperceel-update';
import KadastraalperceelDeleteDialog from './kadastraalperceel-delete-dialog';

const KadastraalperceelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kadastraalperceel />} />
    <Route path="new" element={<KadastraalperceelUpdate />} />
    <Route path=":id">
      <Route index element={<KadastraalperceelDetail />} />
      <Route path="edit" element={<KadastraalperceelUpdate />} />
      <Route path="delete" element={<KadastraalperceelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KadastraalperceelRoutes;
