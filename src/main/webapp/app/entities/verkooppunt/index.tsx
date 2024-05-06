import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verkooppunt from './verkooppunt';
import VerkooppuntDetail from './verkooppunt-detail';
import VerkooppuntUpdate from './verkooppunt-update';
import VerkooppuntDeleteDialog from './verkooppunt-delete-dialog';

const VerkooppuntRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verkooppunt />} />
    <Route path="new" element={<VerkooppuntUpdate />} />
    <Route path=":id">
      <Route index element={<VerkooppuntDetail />} />
      <Route path="edit" element={<VerkooppuntUpdate />} />
      <Route path="delete" element={<VerkooppuntDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerkooppuntRoutes;
