import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Inkooppakket from './inkooppakket';
import InkooppakketDetail from './inkooppakket-detail';
import InkooppakketUpdate from './inkooppakket-update';
import InkooppakketDeleteDialog from './inkooppakket-delete-dialog';

const InkooppakketRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Inkooppakket />} />
    <Route path="new" element={<InkooppakketUpdate />} />
    <Route path=":id">
      <Route index element={<InkooppakketDetail />} />
      <Route path="edit" element={<InkooppakketUpdate />} />
      <Route path="delete" element={<InkooppakketDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InkooppakketRoutes;
