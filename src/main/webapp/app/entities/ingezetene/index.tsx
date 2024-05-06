import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ingezetene from './ingezetene';
import IngezeteneDetail from './ingezetene-detail';
import IngezeteneUpdate from './ingezetene-update';
import IngezeteneDeleteDialog from './ingezetene-delete-dialog';

const IngezeteneRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ingezetene />} />
    <Route path="new" element={<IngezeteneUpdate />} />
    <Route path=":id">
      <Route index element={<IngezeteneDetail />} />
      <Route path="edit" element={<IngezeteneUpdate />} />
      <Route path="delete" element={<IngezeteneDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IngezeteneRoutes;
