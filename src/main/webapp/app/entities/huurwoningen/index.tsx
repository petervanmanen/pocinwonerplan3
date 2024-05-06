import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Huurwoningen from './huurwoningen';
import HuurwoningenDetail from './huurwoningen-detail';
import HuurwoningenUpdate from './huurwoningen-update';
import HuurwoningenDeleteDialog from './huurwoningen-delete-dialog';

const HuurwoningenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Huurwoningen />} />
    <Route path="new" element={<HuurwoningenUpdate />} />
    <Route path=":id">
      <Route index element={<HuurwoningenDetail />} />
      <Route path="edit" element={<HuurwoningenUpdate />} />
      <Route path="delete" element={<HuurwoningenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HuurwoningenRoutes;
