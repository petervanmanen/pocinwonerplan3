import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Inburgeringstraject from './inburgeringstraject';
import InburgeringstrajectDetail from './inburgeringstraject-detail';
import InburgeringstrajectUpdate from './inburgeringstraject-update';
import InburgeringstrajectDeleteDialog from './inburgeringstraject-delete-dialog';

const InburgeringstrajectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Inburgeringstraject />} />
    <Route path="new" element={<InburgeringstrajectUpdate />} />
    <Route path=":id">
      <Route index element={<InburgeringstrajectDetail />} />
      <Route path="edit" element={<InburgeringstrajectUpdate />} />
      <Route path="delete" element={<InburgeringstrajectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InburgeringstrajectRoutes;
