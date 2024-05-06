import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Naderetoegang from './naderetoegang';
import NaderetoegangDetail from './naderetoegang-detail';
import NaderetoegangUpdate from './naderetoegang-update';
import NaderetoegangDeleteDialog from './naderetoegang-delete-dialog';

const NaderetoegangRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Naderetoegang />} />
    <Route path="new" element={<NaderetoegangUpdate />} />
    <Route path=":id">
      <Route index element={<NaderetoegangDetail />} />
      <Route path="edit" element={<NaderetoegangUpdate />} />
      <Route path="delete" element={<NaderetoegangDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NaderetoegangRoutes;
