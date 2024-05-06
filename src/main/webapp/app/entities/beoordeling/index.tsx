import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Beoordeling from './beoordeling';
import BeoordelingDetail from './beoordeling-detail';
import BeoordelingUpdate from './beoordeling-update';
import BeoordelingDeleteDialog from './beoordeling-delete-dialog';

const BeoordelingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Beoordeling />} />
    <Route path="new" element={<BeoordelingUpdate />} />
    <Route path=":id">
      <Route index element={<BeoordelingDetail />} />
      <Route path="edit" element={<BeoordelingUpdate />} />
      <Route path="delete" element={<BeoordelingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BeoordelingRoutes;
