import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Boa from './boa';
import BoaDetail from './boa-detail';
import BoaUpdate from './boa-update';
import BoaDeleteDialog from './boa-delete-dialog';

const BoaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Boa />} />
    <Route path="new" element={<BoaUpdate />} />
    <Route path=":id">
      <Route index element={<BoaDetail />} />
      <Route path="edit" element={<BoaUpdate />} />
      <Route path="delete" element={<BoaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BoaRoutes;
