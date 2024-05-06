import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Mulderfeit from './mulderfeit';
import MulderfeitDetail from './mulderfeit-detail';
import MulderfeitUpdate from './mulderfeit-update';
import MulderfeitDeleteDialog from './mulderfeit-delete-dialog';

const MulderfeitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Mulderfeit />} />
    <Route path="new" element={<MulderfeitUpdate />} />
    <Route path=":id">
      <Route index element={<MulderfeitDetail />} />
      <Route path="edit" element={<MulderfeitUpdate />} />
      <Route path="delete" element={<MulderfeitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MulderfeitRoutes;
