import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Cmdbitem from './cmdbitem';
import CmdbitemDetail from './cmdbitem-detail';
import CmdbitemUpdate from './cmdbitem-update';
import CmdbitemDeleteDialog from './cmdbitem-delete-dialog';

const CmdbitemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Cmdbitem />} />
    <Route path="new" element={<CmdbitemUpdate />} />
    <Route path=":id">
      <Route index element={<CmdbitemDetail />} />
      <Route path="edit" element={<CmdbitemUpdate />} />
      <Route path="delete" element={<CmdbitemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CmdbitemRoutes;
