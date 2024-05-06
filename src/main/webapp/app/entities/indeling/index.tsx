import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Indeling from './indeling';
import IndelingDetail from './indeling-detail';
import IndelingUpdate from './indeling-update';
import IndelingDeleteDialog from './indeling-delete-dialog';

const IndelingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Indeling />} />
    <Route path="new" element={<IndelingUpdate />} />
    <Route path=":id">
      <Route index element={<IndelingDetail />} />
      <Route path="edit" element={<IndelingUpdate />} />
      <Route path="delete" element={<IndelingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IndelingRoutes;
