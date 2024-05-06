import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Videoopname from './videoopname';
import VideoopnameDetail from './videoopname-detail';
import VideoopnameUpdate from './videoopname-update';
import VideoopnameDeleteDialog from './videoopname-delete-dialog';

const VideoopnameRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Videoopname />} />
    <Route path="new" element={<VideoopnameUpdate />} />
    <Route path=":id">
      <Route index element={<VideoopnameDetail />} />
      <Route path="edit" element={<VideoopnameUpdate />} />
      <Route path="delete" element={<VideoopnameDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VideoopnameRoutes;
